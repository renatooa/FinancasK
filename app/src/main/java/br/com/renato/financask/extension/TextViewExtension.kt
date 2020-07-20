package br.com.renato.financask.extension

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.transacao_item.view.*

fun TextView.setTextColorCompat(context: Context, cor: Int){
    this.setTextColor(ContextCompat.getColor(context, cor));
}
