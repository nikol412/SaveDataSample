package com.nikol412.savedatasample.data.response


import com.google.gson.annotations.SerializedName

class WordResponse : ArrayList<WordResponseItem>()

data class WordItemUI(
    val word: String,
    val phonetic: String?,
    val origin: String?,
    val meanings: List<String>?
)

fun WordResponseItem.toIU() =
    WordItemUI(
        this.word,
        this.phonetic,
        this.origin,
        this.meanings?.map { it.definitions.map { it.definition } }?.flatten()
    )

data class WordResponseItem(
    @SerializedName("word")
    val word: String,
    @SerializedName("phonetic")
    val phonetic: String?,
//    @SerializedName("phonetics")
//    val phonetics: List<Phonetic>,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("meanings")
    val meanings: List<Meaning>?
)

data class Definition(
    @SerializedName("definition")
    val definition: String,
    @SerializedName("example")
    val example: String,
    @SerializedName("synonyms")
    val synonyms: List<Any>,
    @SerializedName("antonyms")
    val antonyms: List<Any>
)

data class Phonetic(
    @SerializedName("text")
    val text: String,
    @SerializedName("audio")
    val audio: String
)

data class Meaning(
    @SerializedName("partOfSpeech")
    val partOfSpeech: String,
    @SerializedName("definitions")
    val definitions: List<Definition>
)