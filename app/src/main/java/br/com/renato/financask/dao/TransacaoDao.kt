package br.com.renato.financask.dao

import br.com.renato.financask.model.Transacao
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class TransacaoDao {

    val transacoes : List<Transacao> = Companion.transacoes

    companion object {
        private val transacoes : MutableList<Transacao> = mutableListOf()
    }

    fun remover(posicao : Int) {
        Companion.transacoes.removeAt(posicao)
    }

    fun alterar(
        posicao : Int,
        transacaoDelegada : Transacao
    ) {
        Companion.transacoes[posicao] = transacaoDelegada

    }

    fun inserir(transacao : Transacao) {
        Companion.transacoes.add(transacao)
    }
}