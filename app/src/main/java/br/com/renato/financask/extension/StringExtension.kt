package br.com.renato.financask.extension

fun String.limitaCaracteres(caracteres: Int):String{

    if (this.length > caracteres){
        val startIndex = 0
        return  "${this.substring(startIndex, caracteres)}..."
    }
    return  this
}
