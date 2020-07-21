package br.com.renato.financask.delegade

import br.com.renato.financask.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)
}