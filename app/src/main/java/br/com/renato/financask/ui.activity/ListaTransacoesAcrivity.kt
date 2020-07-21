package br.com.renato.financask.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.renato.financask.R
import br.com.renato.financask.extension.formatarData
import br.com.renato.financask.model.Tipo.DESPESA
import br.com.renato.financask.model.Tipo.RECEITA
import br.com.renato.financask.model.Transacao
import br.com.renato.financask.ui.ResumoView
import br.com.renato.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesAcrivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes);

        val transacoes: List<Transacao> = transacoesExemplos()

        configuraResumo(transacoes)
        configurarLista(transacoes)

        lista_transacoes_adiciona_receita.setOnClickListener {
            val view: View = window.decorView

            val viewCriada = LayoutInflater.from(this)
                .inflate(
                    R.layout.form_transacao,
                    view as ViewGroup,
                    false
                )

            val hoje = Calendar.getInstance()

            viewCriada.form_transacao_data
                .setOnClickListener {
                    DatePickerDialog(this,
                        DatePickerDialog.OnDateSetListener { view, ano, mes, dia ->
                            val dataSelecionada = Calendar.getInstance()
                            dataSelecionada.set(ano, mes, dia)
                            viewCriada.form_transacao_data
                                .setText(dataSelecionada.formatarData())
                        }
                        ,
                        hoje.get(Calendar.YEAR),
                        hoje.get(Calendar.MONTH),
                        hoje.get(Calendar.DAY_OF_MONTH))
                        .show()
                }

            val adapter = ArrayAdapter
                .createFromResource(
                    this,
                    R.array.categorias_de_receita,
                    android.R.layout.simple_spinner_dropdown_item
                )

            viewCriada.form_transacao_categoria.adapter = adapter

            AlertDialog.Builder(this)
                .setTitle(R.string.adiciona_receita)
                .setView(viewCriada)
                .setPositiveButton(
                    "add",
                    { alerta, opcao -> Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show()})
                .setNegativeButton("Cancelar", { alerta, opcao -> Toast.makeText(this, "Can", Toast.LENGTH_SHORT).show()})
                .show()
        }
    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val resumoView = ResumoView(window.decorView, transacoes)
        resumoView.atualizar();
    }


    private fun configurarLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesExemplos(): List<Transacao> {
        val transacoes = listOf<Transacao>(
            Transacao(BigDecimal(20.50), "Comida"),
            Transacao(BigDecimal(50.00), RECEITA),
            Transacao(valor = BigDecimal(50.00), tipo = DESPESA),
            Transacao(categoria = "Economia", tipo = RECEITA, valor = BigDecimal(100))
        )
        return transacoes
    }
}