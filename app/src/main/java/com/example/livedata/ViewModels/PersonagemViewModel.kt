package com.example.livedata.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livedata.Entites.Personagem
import com.example.livedata.DataBASE.AppDatabase
import kotlinx.coroutines.launch

class PersonagemViewModel(private val db: AppDatabase) : ViewModel() {

    // LiveData para observar a lista de personagens
    private val _personagens = MutableLiveData<List<Personagem>>()
    val personagens: LiveData<List<Personagem>> get() = _personagens

    // Função para buscar todos os personagens
    fun buscarPersonagens() {
        viewModelScope.launch {
            val personagensList = db.personagemDAO().selectAll()
            _personagens.value = personagensList

            // Exibindo os personagens no log (ou em outro lugar se necessário)
            for (personagem in personagensList) {
                println("Nome: ${personagem.nome}, Nível: ${personagem.nivel}")
            }
        }
    }



}
