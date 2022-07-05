package com.nikol412.savedatasample.utils

import com.nikol412.savedatasample.data.entity.WordEntity
import com.nikol412.savedatasample.data.response.WordItemUI

fun WordEntity.toUi() = WordItemUI(
    this.word,
    this.phonetic,
    this.origin,
    this.meanings
)

fun WordItemUI.toEntity() = WordEntity(
    0,
    this.word,
    this.phonetic,
    this.origin,
    this.meanings
)
