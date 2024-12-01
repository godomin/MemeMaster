package com.ykim.mememaster.presentation.home

import com.ykim.mememaster.R

fun getSearchResult(query: String): List<Int> {
    return if (query.isEmpty()) {
        keywordMap.map { it.key }
    } else {
        keywordMap.filter { entry ->
            entry.value.any { it.contains(query, ignoreCase = true) }
        }.map { it.key }
    }
}

private val keywordMap = mapOf(
    R.drawable.template_01 to listOf("disaster", "meme", "irony"),
    R.drawable.template_02 to listOf("friendship", "strength", "unity"),
    R.drawable.template_03 to listOf("choice", "decision", "humor"),
    R.drawable.template_04 to listOf("loneliness", "sadness", "waiting"),
    R.drawable.template_05 to listOf("debate", "opinion", "challenge"),
    R.drawable.template_06 to listOf("dilemma", "options", "stress"),
    R.drawable.template_07 to listOf("smile", "hidden pain", "meme"),
    R.drawable.template_08 to listOf("reaction", "shock", "humor"),
    R.drawable.template_09 to listOf("presentation", "plan", "failure"),
    R.drawable.template_10 to listOf("relationship", "misunderstanding", "distance"),
    R.drawable.template_11 to listOf("suspicion", "doubt", "meme"),
    R.drawable.template_12 to listOf("confusion", "duplicate", "identity"),
    R.drawable.template_13 to listOf("organization", "tasks", "list"),
    R.drawable.template_14 to listOf("support", "unexpected", "awkward"),
    R.drawable.template_15 to listOf("preference", "reaction", "contrast"),
    R.drawable.template_16 to listOf("business", "awkwardness", "comedy"),
    R.drawable.template_17 to listOf("transformation", "strength", "cartoon"),
    R.drawable.template_18 to listOf("sadness", "cartoon", "regret"),
    R.drawable.template_19 to listOf("reaction", "emotion", "commentary"),
    R.drawable.template_20 to listOf("comparison", "size", "books"),
    R.drawable.template_21 to listOf("questioning", "butterfly", "meme"),
    R.drawable.template_22 to listOf("headache", "stress", "comparison"),
    R.drawable.template_23 to listOf("escape", "pirate", "chase"),
    R.drawable.template_24 to listOf("celebration", "toast", "luxury"),
    R.drawable.template_25 to listOf("whisper", "goosebumps", "intense"),
    R.drawable.template_26 to listOf("disaster", "reaction", "selfie"),
    R.drawable.template_27 to listOf("overthinking", "night", "brain"),
    R.drawable.template_28 to listOf("dilemma", "choice", "pressure"),
    R.drawable.template_29 to listOf("reliable", "box", "cartoon"),
    R.drawable.template_30 to listOf("protest", "sign", "empty"),
    R.drawable.template_31 to listOf("transformation", "clown", "irony"),
    R.drawable.template_32 to listOf("deception", "strategy", "Trojan"),
    R.drawable.template_33 to listOf("call", "decision", "options"),
    R.drawable.template_34 to listOf("surprise", "cat", "expression"),
    R.drawable.template_35 to listOf("office", "boredom", "routine"),
    R.drawable.template_36 to listOf("meeting", "frustration", "humor"),
    R.drawable.template_37 to listOf("excitement", "energy", "cartoon"),
    R.drawable.template_38 to listOf("medication", "reaction", "confusion"),
    R.drawable.template_39 to listOf("introspection", "mirror", "self"),
    R.drawable.template_40 to listOf("sadness", "tears", "emotion"),
    R.drawable.template_41 to listOf("tonite", "text conversation", "funny"),
    R.drawable.template_42 to listOf("skeleton", "waiting", "patience"),
    R.drawable.template_43 to listOf("Simpsons", "realization", "funny"),
    R.drawable.template_44 to listOf("orangutan", "awkward silence", "TV panel"),
    R.drawable.template_45 to listOf("Mr. Bean", "waiting", "patience"),
    R.drawable.template_46 to listOf("Spiderman pointing", "confusion", "blame"),
    R.drawable.template_47 to listOf("Disney character", "before and after", "contrast"),
    R.drawable.template_48 to listOf("Ben Affleck", "exhausted", "relatable"),
    R.drawable.template_49 to listOf("flex tape", "leak fix", "meme"),
)
