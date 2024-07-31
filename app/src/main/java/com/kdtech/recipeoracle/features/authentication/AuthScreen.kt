package com.kdtech.recipeoracle.features.authentication
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kdtech.recipeoracle.R

@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    val context = LocalContext.current
    val googleSignInClient = remember { getGoogleSignInClient(context) }
    val firebaseAuth = remember { FirebaseAuth.getInstance() }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(firebaseAuth, account)
        } catch (e: ApiException) {
            // Google Sign In failed
            Log.e("aaa", "AuthScreen: exception: $e")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }) {
            Text(text = "Sign in with Google")
        }
    }
}

private fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

private fun firebaseAuthWithGoogle(firebaseAuth: FirebaseAuth, acct: GoogleSignInAccount?) {
    val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
    firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e("aaa", "AuthScreen: success: $task")
            } else {
                Log.e("aaa", "AuthScreen: fail: $task")

            }
        }
}
