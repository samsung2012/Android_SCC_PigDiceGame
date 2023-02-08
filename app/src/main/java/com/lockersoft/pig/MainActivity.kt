package com.lockersoft.pig

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

const val RESET_SCORE_ON_ONES = true  // set to true for reg. play
const val MAX_SCORE = 100 // set to 100 for reg. play

class MainActivity : AppCompatActivity() {
    lateinit var tvYouPlaying: TextView
    lateinit var tvCompPlaying: TextView
    lateinit var tvYouGamesWon: TextView
    lateinit var tvCompGamesWon: TextView
    lateinit var tvYouTotalScore: TextView
    lateinit var tvCompTotalScore: TextView
    lateinit var tvYouTurnTotal: TextView
    lateinit var tvCompTurnTotal: TextView
    lateinit var ivLeftDice: ImageView
    lateinit var ivRightDice: ImageView
    lateinit var ivWinner: ImageView
    lateinit var ivLoser: ImageView
    lateinit var diceTotal: TextView
    lateinit var btnRoll: Button
    lateinit var btnHold: Button
    var youGamesWonInt = 0
    var compGamesWonInt = 0
    var youTotalScoreInt = 0
    var compTotalScoreInt = 0
    var youTurnTotalInt = 0
    var compTurnTotalInt = 0
    var diceRollLeftInt = 0
    var diceRollRightInt = 0
    var diceTotalInt = 0
    val dice = Dice(4)
    var player = true
    var computer = false
    var compLost = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initApplication()
        Log.i("Callback", "inside onCreate()")
    }

    private fun initApplication() {
        tvYouPlaying = findViewById(R.id.youPlaying)
        tvCompPlaying = findViewById(R.id.compPlaying)
        tvYouGamesWon = findViewById(R.id.tvYouGamesWon)
        tvCompGamesWon = findViewById(R.id.tvCompGamesWon)
        tvYouTotalScore = findViewById(R.id.tvYouTotalScore)
        tvCompTotalScore = findViewById(R.id.tvCompTotalScore)
        tvYouTurnTotal = findViewById(R.id.tvYouTurnTotal)
        tvCompTurnTotal = findViewById(R.id.compTurnTotal)
        ivLeftDice = findViewById(R.id.imageViewLeftDice)
        ivRightDice = findViewById(R.id.imageViewRightDice)
        ivWinner = findViewById(R.id.ivWinner)
        ivLoser = findViewById(R.id.ivLoser)
        diceTotal = findViewById(R.id.diceTotal)
        btnRoll = findViewById(R.id.btnRoll)
        btnHold = findViewById(R.id.btnHold)
    }

    fun onBtnRollClick(v: View) {
        tvYouPlaying.setBackgroundColor(Color.CYAN)
        tvCompPlaying.setBackgroundColor(Color.WHITE)
        player = true
        computer = false
        btnHold.isEnabled = true
        Log.i("Callback", "Inside onBtnRollClick()")
        rollDice()
        updateDiceImages()
        updateTurn()

        if (player && (diceRollLeftInt == 1 && diceRollRightInt == 1)) {
            youTurnTotalInt = 0
            tvYouTurnTotal.text = "0"
            youTotalScoreInt = 0
            tvYouTotalScore.text = "0"
            Log.i("Callback", "1 on BOTH dice, inside onBtnRollClick()")
            switchPlayer()
            implementCountDown()

        } else if (player && (diceRollLeftInt == 1 || diceRollRightInt == 1)) {
            youTurnTotalInt = 0
            tvYouTurnTotal.text = "0"
            Log.i("Callback", "1 on one dice, inside onBtnRollClick()")
            switchPlayer()
            implementCountDown()
        }
    }

    fun onBtnHoldClick(v: View) {
        Log.i("Callback", "Inside onBtnHoldClick()")
        saveTurnScore()

        if (player && youTotalScoreInt >= MAX_SCORE) {
            youGamesWonInt++
            tvYouGamesWon.text = "$youGamesWonInt"
            ivWinner.setImageResource(R.drawable.winner)
            ivWinner.visibility = View.VISIBLE
        } else {
            switchPlayer()
            implementCountDown()
        }
    }

    fun implementCountDown() {
        object : CountDownTimer(6000, 2000) {
            override fun onTick(millisUntilFinished: Long) {
                if (!compLost) {
                    rollDice()
                    updateDiceImages()
                    updateTurn()
                }
                if (computer && (diceRollLeftInt == 1 && diceRollRightInt == 1)) {
                    compTurnTotalInt = 0
                    tvCompTurnTotal.text = "0"
                    compTotalScoreInt = 0
                    tvCompTotalScore.text = "0"
                    compLost = true
                    Log.i("Callback", "1 on BOTH dice, inside onTick()")
                }
                if (computer && (diceRollLeftInt == 1 || diceRollRightInt == 1)) {
                    compTurnTotalInt = 0
                    tvCompTurnTotal.text = "0"
                    compLost = true
                    Log.i("Callback", "1 on one dice, inside onTick()")
                }
            }

            override fun onFinish() {
                //oneDiceSingle()
                //oneDiceDouble()
                compLost = false
                Log.i("Callback", "Inside onFinish()")
                saveTurnScore()
                winLoseLabel()
                switchPlayer()
            }
        }.start()
    }

    private fun rollDice() {
        diceRollLeftInt = dice.roll()
        diceRollRightInt = dice.roll()
    }

    fun winLoseLabel() {
        if (player && youTotalScoreInt >= MAX_SCORE) {
            youGamesWonInt++
            tvYouGamesWon.text = "$youGamesWonInt"
            ivWinner.setImageResource(R.drawable.winner)
            ivWinner.visibility = View.VISIBLE
        } else if (computer && compTotalScoreInt >= MAX_SCORE) {
            compGamesWonInt++
            tvCompGamesWon.text = "$compGamesWonInt"
            ivLoser.setImageResource(R.drawable.loser)
            ivLoser.visibility = View.VISIBLE
        }
    }

    fun updateDiceImages() {
        diceTotal.text = (diceRollLeftInt + diceRollRightInt).toString()

        when (diceRollLeftInt) {
            1 -> {
                ivLeftDice.setImageResource(R.drawable.dice_1)
                Log.i("callback", "Rolled a One on Left Side")
            }
            2 -> ivLeftDice.setImageResource(R.drawable.dice_2)
            3 -> ivLeftDice.setImageResource(R.drawable.dice_3)
            4 -> ivLeftDice.setImageResource(R.drawable.dice_4)
            5 -> ivLeftDice.setImageResource(R.drawable.dice_5)
            6 -> ivLeftDice.setImageResource(R.drawable.dice_6)
        }

        when (diceRollRightInt) {
            1 -> {
                ivRightDice.setImageResource(R.drawable.dice_1)
                Log.i("callback", "Rolled a One on Right Side")
            }
            2 -> ivRightDice.setImageResource(R.drawable.dice_2)
            3 -> ivRightDice.setImageResource(R.drawable.dice_3)
            4 -> ivRightDice.setImageResource(R.drawable.dice_4)
            5 -> ivRightDice.setImageResource(R.drawable.dice_5)
            6 -> ivRightDice.setImageResource(R.drawable.dice_6)
        }
    }

    fun updateTurn() {
        if (player) {
            diceTotalInt = diceRollLeftInt + diceRollRightInt
            youTurnTotalInt += diceTotalInt
            tvYouTurnTotal.text = youTurnTotalInt.toString()
        } else if (computer) {
            diceTotalInt = diceRollLeftInt + diceRollRightInt
            compTurnTotalInt += diceTotalInt
            tvCompTurnTotal.text = compTurnTotalInt.toString()
        }
    }

    fun onWinLoseImageClick(v: View) {
        player = true
        computer = false
        tvYouPlaying.setBackgroundColor(Color.CYAN)
        tvCompPlaying.setBackgroundColor(Color.WHITE)
        btnRoll.isEnabled = true
        btnHold.isEnabled = false
        diceTotalInt = 0
        diceTotal.text = "0"
        youTurnTotalInt = 0
        compTurnTotalInt = 0
        youTotalScoreInt = 0
        compTotalScoreInt = 0
        tvYouTurnTotal.text = "0"
        tvCompTurnTotal.text = "0"
        tvYouTotalScore.text = "0"
        tvCompTotalScore.text = "0"
        ivLoser.visibility = View.INVISIBLE
        ivWinner.visibility = View.INVISIBLE
    }

    fun saveTurnScore() {
        if (player) {
            youTotalScoreInt += youTurnTotalInt
            tvYouTotalScore.text = youTotalScoreInt.toString()
            tvYouTurnTotal.text = "0"
            youTurnTotalInt = 0

        } else if (computer) {
            compTotalScoreInt += compTurnTotalInt
            tvCompTotalScore.text = compTotalScoreInt.toString()
            tvCompTurnTotal.text = "0"
            compTurnTotalInt = 0
        }
    }

    fun switchPlayer() {
        if (player) {
            player = false
            computer = true
            tvYouPlaying.setBackgroundColor(Color.WHITE)
            tvCompPlaying.setBackgroundColor(Color.CYAN)
            btnRoll.isEnabled = false
            btnHold.isEnabled = true
            Log.i("callback", "Switched from Player to Computer")

        } else if (computer) {
            player = true
            computer = false
            tvYouPlaying.setBackgroundColor(Color.CYAN)
            tvCompPlaying.setBackgroundColor(Color.WHITE)
            btnRoll.isEnabled = true
            btnHold.isEnabled = false
            Log.i("callback", "Switched from Computer to Player")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("Callback", "inside onStart()")
        tvYouPlaying.setBackgroundColor(Color.CYAN)
        btnHold.isEnabled = false
    }

    override fun onResume() {
        super.onResume()
        Log.i("Callback", "inside onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Callback", "inside onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Callback", "inside onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Callback", "inside onDestroy()")
    }
}

class Dice(var numSides: Int) {
    fun roll(): Int {
        if (RESET_SCORE_ON_ONES) {
            return (1..numSides).random()
        } else {
            return (2..numSides).random()
        }
    }
}