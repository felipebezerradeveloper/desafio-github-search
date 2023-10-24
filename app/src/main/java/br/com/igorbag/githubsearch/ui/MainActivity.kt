/*package br.com.igorbag.githubsearch.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.igorbag.githubsearch.R
import br.com.igorbag.githubsearch.data.GitHubService
import br.com.igorbag.githubsearch.domain.Repository

class MainActivity : AppCompatActivity() {

    lateinit var nomeUsuario: EditText
    lateinit var btnConfirmar: Button
    lateinit var listaRepositories: RecyclerView
    lateinit var githubApi: GitHubService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        showUserName()
        setupRetrofit()
        getAllReposByUserName()
    }

    // Metodo responsavel por realizar o setup da view e recuperar os Ids do layout
    fun setupView() {
        //@TODO 1 - Recuperar os Id's da tela para a Activity com o findViewById
    }

    //metodo responsavel por configurar os listeners click da tela
    private fun setupListeners() {
        //@TODO 2 - colocar a acao de click do botao confirmar
    }


    // salvar o usuario preenchido no EditText utilizando uma SharedPreferences
    private fun saveUserLocal() {
        //@TODO 3 - Persistir o usuario preenchido na editText com a SharedPref no listener do botao salvar
    }

    private fun showUserName() {
        //@TODO 4- depois de persistir o usuario exibir sempre as informacoes no EditText  se a sharedpref possuir algum valor, exibir no proprio editText o valor salvo
    }

    //Metodo responsavel por fazer a configuracao base do Retrofit
    fun setupRetrofit() {
        /*
           @TODO 5 -  realizar a Configuracao base do retrofit
           Documentacao oficial do retrofit - https://square.github.io/retrofit/
           URL_BASE da API do  GitHub= https://api.github.com/
           lembre-se de utilizar o GsonConverterFactory mostrado no curso
        */
    }

    //Metodo responsavel por buscar todos os repositorios do usuario fornecido
    fun getAllReposByUserName() {
        // TODO 6 - realizar a implementacao do callback do retrofit e chamar o metodo setupAdapter se retornar os dados com sucesso
    }

    // Metodo responsavel por realizar a configuracao do adapter
    fun setupAdapter(list: List<Repository>) {
        /*
            @TODO 7 - Implementar a configuracao do Adapter , construir o adapter e instancia-lo
            passando a listagem dos repositorios
         */
    }


    // Metodo responsavel por compartilhar o link do repositorio selecionado
    // @Todo 11 - Colocar esse metodo no click do share item do adapter
    fun shareRepositoryLink(urlRepository: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlRepository)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    // Metodo responsavel por abrir o browser com o link informado do repositorio

    // @Todo 12 - Colocar esse metodo no click item do adapter
    fun openBrowser(urlRepository: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlRepository)
            )
        )

    }

}*/

package br.com.igorbag.githubsearch.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.igorbag.githubsearch.R
import br.com.igorbag.githubsearch.data.GitHubService
import br.com.igorbag.githubsearch.domain.Repository
import br.com.igorbag.githubsearch.ui.adapter.RepositoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var nomeUsuario: EditText
    private lateinit var btnConfirmar: Button
    private lateinit var listaRepositories: RecyclerView
    private lateinit var githubApi: GitHubService
    private lateinit var repositoryAdapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
        setupRetrofit()
        getAllReposByUserName()
    }

    // Método responsável por realizar o setup da view e recuperar os Ids do layout
    private fun setupView() {
        nomeUsuario = findViewById(R.id.rv_lista_repositories)
        btnConfirmar = findViewById(R.id.btn_confirmar)
        listaRepositories = findViewById(R.id.tv_titulo)
    }

    // Método responsável por configurar os listeners de clique
    private fun setupListeners() {
        btnConfirmar.setOnClickListener {
            saveUserLocal()
        }
    }

    // Salvar o usuário preenchido no EditText utilizando SharedPreferences
    private fun saveUserLocal() {
        val userName = nomeUsuario.text.toString()
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("userName", userName)
        editor.apply()
    }

    private fun showUserName() {
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val savedUserName = sharedPref.getString("userName", "")
        if (!savedUserName.isNullOrBlank()) {
            nomeUsuario.setText(savedUserName)
        }
    }

    // Método responsável por fazer a configuração base do Retrofit
    private fun setupRetrofit() {
        githubApi = GitHubService.create()
    }

    // Método responsável por buscar todos os repositórios do usuário fornecido
    private fun getAllReposByUserName() {
        showUserName()
        val userName = "felipebezerradeveloper" // Substitua pelo nome de usuário correto
        githubApi.getRepositoriesForUser(userName).enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { repositories ->
                        setupAdapter(repositories)
                    }
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    // Método responsável por configurar o adapter
    private fun setupAdapter(list: List<Repository>) {
        repositoryAdapter = RepositoryAdapter(list) { repository ->
            shareRepositoryLink(repository.html_url)
            openBrowser(repository.html_url)
        }
        listaRepositories.adapter = repositoryAdapter
    }

    private fun shareRepositoryLink(urlRepository: String) {
        TODO("Not yet implemented")
    }

    // Método responsável por compartilhar o link do repositório selecionado
    private fun shareRepositoryLink(urlRepository: String, htmlUrl: Any?) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlRepository)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun Intent(actionView: String, parse: Uri?, c: Char) {

    }

    // Método responsável por abrir o browser com o link informado do repositório
    private fun openBrowser(urlRepository: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlRepository))
    }
}
