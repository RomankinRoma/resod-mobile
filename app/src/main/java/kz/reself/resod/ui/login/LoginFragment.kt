package kz.reself.resod.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ClientProtocolException
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils
import kz.reself.resod.R
import kz.reself.resod.RegistrationActivity
import kz.reself.resod.databinding.FragmentLoginBinding
import java.io.IOException
import java.util.Arrays.asList


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 0
    private lateinit var callbackManager: CallbackManager
//    private lateinit var accessToken: AccessToken

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentLoginBinding.inflate(inflater, container,false)

        val root: View = binding.root

        binding.fragmentLoginRegistrationBtn.setOnClickListener {
            val intent = Intent(this@LoginFragment.requireContext(), RegistrationActivity::class.java)
            startActivity(intent)
        }

        // Facebook start
        callbackManager = CallbackManager.Factory.create()

        val accessToken = AccessToken.getCurrentAccessToken()

        if (accessToken != null && accessToken.isExpired == false) {
            LoginManager.getInstance().logOut()
        }

        binding.fragmentLoginSignInButtonFacebook.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {
                TODO("Not yet implemented")
            }

            override fun onSuccess(result: LoginResult) {
                val intent = Intent(this@LoginFragment.requireContext(), RegistrationActivity::class.java)
                startActivity(intent)
            }
        })

        binding.fragmentLoginSignInButtonFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, asList("public_profile"))
        }
        // Facebook end


        // Google start
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding.fragmentLoginSignInButtonGoogle.setOnClickListener {
            if (it.id === R.id.fragment_login_sign_in_button_google) {
                signIn()
            }
        }

        if (account != null) {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                Toast.makeText(context, "User signOut!!!", Toast.LENGTH_SHORT).show()
            }
        }
        // Google end

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    // Google sign in
    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

        // Google
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        // Facebook
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Google
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken != null) {
                print("------ TOKEN VAL ----: " + idToken)
                Log.w("TOKEN_VAL", "TOKEN" + idToken)
//                sendIdTokenByServer(idToken)
                Navigation.findNavController(binding.root).navigate(R.id.action_fragment_login_nav_to_fragment_profile)
            }

            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SIGN IN GOOGLE", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun sendIdTokenByServer(idToken: String) {
        val httpClient: HttpClient = DefaultHttpClient()
        val httpPost = HttpPost("https://resod.kz/oauth2/authorize/google?redirect_uri=myandroidapp://oauth2/redirect")

        try {
            val nameValuePairs: MutableList<NameValuePair> = ArrayList(1)
            nameValuePairs.add(BasicNameValuePair("idToken", idToken))
            httpPost.entity = UrlEncodedFormEntity(nameValuePairs)
            val response: HttpResponse = httpClient.execute(httpPost)
            val statusCode: Int = response.getStatusLine().getStatusCode()
            val responseBody = EntityUtils.toString(response.getEntity())
            Log.w("GOOGLE_SIGN_IN_BACKED", "Signed in as: $responseBody")
            Log.w("GOOGLE_SIGN_IN_BACKED", "STATUS CODE: $statusCode")
        } catch (e: ClientProtocolException) {
            Log.w("GOOGLE_SIGN_IN_BACKED", "Error sending ID token to backend.", e)
        } catch (e: IOException) {
            Log.w("GOOGLE_SIGN_IN_BACKED", "Error sending ID token to backend.", e)
        }
    }
}