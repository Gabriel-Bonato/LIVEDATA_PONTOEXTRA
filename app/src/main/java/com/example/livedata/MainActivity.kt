package com.example.livedata

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.example.livedata.DataBASE.AppDatabase
import com.example.livedata.Entites.Personagem
import com.example.livedata.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    // *******************************************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        val personagemDAO = db.personagemDAO()
        lifecycleScope.launch {
            val novoPersonagem = Personagem(nome = "Legolas", nivel = 10)
            personagemDAO.insert(novoPersonagem)
        }

        personagemDAO.selectAllLiveData().observe(this) { personagens ->
            for (personagem in personagens) {
                println("Nome: ${personagem.nome}, Nível: ${personagem.nivel}")
            }
        }

        lifecycleScope.launch {
            val personagemExistente = personagemDAO.selectAll().first()
            val personagemAtualizado = personagemExistente.copy(nivel = 20)
            personagemDAO.update(personagemAtualizado)
        }

        lifecycleScope.launch {
            val personagemParaDeletar = personagemDAO.selectAll().first()
            personagemDAO.delete(personagemParaDeletar)
        }


    }
    // *******************************************************************************************************************

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}