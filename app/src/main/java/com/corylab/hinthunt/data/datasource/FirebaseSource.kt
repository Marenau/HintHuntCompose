package com.corylab.hinthunt.data.datasource

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseSource {
    private var database: DatabaseReference = Firebase.database.reference

    private var uniqueKey: String = ""
    private var room: String = ""
    private var size: Int = 0

    fun initiateKey(key: String) {
        uniqueKey = key
        room = StringBuilder().append("room_").append(key).toString()
    }

    fun putKey(key: String) {
        uniqueKey = key
        room = StringBuilder().append("room_").append(key).toString()
        database.child("rooms").child(room).child("room_id").setValue(key)
    }

    fun putWords(words: List<String>) {
        size = words.size
        for (i in words.indices) {
            database.child("rooms").child(room).child("words").child(i.toString()).setValue(words[i])
        }
    }

    fun putNumOfCards(command: Int, num: Int) {
        when (command) {
            1 -> database.child("rooms").child(room).child("first_num").setValue(num)
            2 -> database.child("rooms").child(room).child("second_num").setValue(num)
        }
    }

    fun putScore(command: Int, score: Int) {
        when (command) {
            1 -> database.child("rooms").child(room).child("first_score").setValue(score)
            2 -> database.child("rooms").child(room).child("second_score").setValue(score)
        }
    }

    fun putTurn(turn: Int) {
        database.child("rooms").child(room).child("turn").setValue(turn)
    }

    fun putColorNums(colors: List<Int>) {
        for (i in colors.indices) {
            database.child("rooms").child(room).child("colors").child(i.toString())
                .setValue(colors[i])
        }
    }

    fun putTeamsColors(numOfColors: Int) {
        database.child("rooms").child(room).child("teams_colors").setValue(numOfColors)
    }

    fun putSelectedColor(selectedColors: List<Boolean>) {
        for (i in selectedColors.indices) {
            database.child("rooms").child(room).child("selected_colors").child(i.toString())
                .setValue(selectedColors[i])
        }
    }

    fun putWinner(winner: Int) {
        database.child("rooms").child(room).child("winner").setValue(winner)
    }

    fun putSelectedColor(index: Int, state: Boolean) {
        database.child("rooms").child(room).child("selected_colors").child(index.toString())
            .setValue(state)
    }

    fun putComplexity(complexity: Int) {
        database.child("rooms").child(room).child("words_complexity").setValue(complexity)
    }

    fun putLang(lang: Int) {
        database.child("rooms").child(room).child("words_language").setValue(lang)
    }

    fun putSize(size: Int) {
        database.child("rooms").child(room).child("words_size").setValue(size)
    }

    fun getWords(): Flow<List<String>> = callbackFlow {
        val wordsRef = database.child("rooms").child(room).child("words")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val words = mutableListOf<String>()
                snapshot.children.mapNotNullTo(words) { it.getValue(String::class.java) }
                trySend(words)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        wordsRef.addValueEventListener(listener)
        awaitClose { wordsRef.removeEventListener(listener) }
    }

    fun getNumOfCards(command: Int): Flow<Int> = callbackFlow {
        val numRef = when (command) {
            1 -> database.child("rooms").child(room).child("first_num")
            else -> database.child("rooms").child(room).child("second_num")
        }
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val num = snapshot.getValue<Int>()!!
                trySend(num)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        numRef.addValueEventListener(listener)
        awaitClose { numRef.removeEventListener(listener) }
    }

    fun getScore(command: Int): Flow<Int> = callbackFlow {
        val numRef = when (command) {
            1 -> database.child("rooms").child(room).child("first_score")
            else -> database.child("rooms").child(room).child("second_score")
        }
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val num = snapshot.getValue<Int>()!!
                trySend(num)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        numRef.addValueEventListener(listener)
        awaitClose { numRef.removeEventListener(listener) }
    }

    fun getTurn(): Flow<Int> = callbackFlow {
        val turnRef = database.child("rooms").child(room).child("turn")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val turn = snapshot.getValue<Int>()!!
                trySend(turn)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        turnRef.addValueEventListener(listener)
        awaitClose { turnRef.removeEventListener(listener) }
    }

    fun getColorNums(): Flow<List<Int>> = callbackFlow {
        val colorsRef = database.child("rooms").child(room).child("colors")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val colors = mutableListOf<Int>()
                snapshot.children.mapNotNullTo(colors) { it.getValue(Int::class.java) }
                trySend(colors)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        colorsRef.addValueEventListener(listener)
        awaitClose { colorsRef.removeEventListener(listener) }
    }

    fun getTeamsColors(): Flow<Int> = callbackFlow {
        val turnRef = database.child("rooms").child(room).child("teams_colors")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val turn = snapshot.getValue<Int>()!!
                trySend(turn)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        turnRef.addValueEventListener(listener)
        awaitClose { turnRef.removeEventListener(listener) }
    }

    fun getWinner(): Flow<Int> = callbackFlow {
        val turnRef = database.child("rooms").child(room).child("winner")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val turn = snapshot.getValue<Int>()!!
                trySend(turn)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        turnRef.addValueEventListener(listener)
        awaitClose { turnRef.removeEventListener(listener) }
    }

    fun getSelectedColors(): Flow<List<Boolean>> = callbackFlow {
        val colorsRef = database.child("rooms").child(room).child("selected_colors")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val colors = mutableListOf<Boolean>()
                snapshot.children.mapNotNullTo(colors) { it.getValue(Boolean::class.java) }
                trySend(colors)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        colorsRef.addValueEventListener(listener)
        awaitClose { colorsRef.removeEventListener(listener) }
    }

    fun getComplexity(): Flow<Int> = callbackFlow {
        val turnRef = database.child("rooms").child(room).child("words_complexity")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val complexity = snapshot.getValue<Int>()!!
                trySend(complexity)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        turnRef.addValueEventListener(listener)
        awaitClose { turnRef.removeEventListener(listener) }
    }

    fun getLang(): Flow<Int> = callbackFlow {
        val turnRef = database.child("rooms").child(room).child("words_language")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lang = snapshot.getValue<Int>()!!
                trySend(lang)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        turnRef.addValueEventListener(listener)
        awaitClose { turnRef.removeEventListener(listener) }
    }

    fun getSize(): Flow<Int> = callbackFlow {
        val turnRef = database.child("rooms").child(room).child("words_size")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val size = snapshot.getValue<Int>()!!
                trySend(size)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        turnRef.addValueEventListener(listener)
        awaitClose { turnRef.removeEventListener(listener) }
    }
}