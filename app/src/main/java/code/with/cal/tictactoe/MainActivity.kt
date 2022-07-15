package code.with.cal.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import code.with.cal.tictactoe.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    enum class Turn
    {
        PLAYER1,
        PLAYER2
    }

    private var firstTurn = Turn.PLAYER2
    private var currentTurn = Turn.PLAYER2

    private var player2Sore = 0
    private var player1Score = 0

    private var boardList = mutableListOf<Button>()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

        //Add apply plugin: "kotlin-android-extensions" to your build.gradle
        // btnNewGame is the Button id
        btnNewGame.setOnClickListener {
            resetBoard()
        }

    }


    private fun initBoard()
    {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View)
    {
        if(view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(PLAYER1))
        {
            player1Score++
            result("Player 1: O Wins!")
        }
        else if(checkForVictory(PLAYER2))
        {
            player2Sore++
            result("Player 2: X Wins!")
        }

        if(fullBoard())
        {
            result("Draw")
        }


    }

    private fun checkForVictory(s: String): Boolean
    {
        //Horizontal Victory
        if(match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true
        if(match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true
        if(match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true

        //Vertical Victory
        if(match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true
        if(match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true
        if(match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true

        //Diagonal Victory
        if(match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if(match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true

        return false
    }

    //Match the button with the symbol X or 0
    private fun match(button: Button, symbol : String): Boolean = button.text == symbol

    private fun result(title: String)
    {
        val message = "\nPlayer    Score \n------------------------\nPlayer 1   $player1Score\n\nPlayer 2   $player2Sore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("New Game")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard()
    {
        for(button in boardList)
        {
            button.text = ""
        }
        if(firstTurn == Turn.PLAYER1)
            firstTurn = Turn.PLAYER2
        else if(firstTurn == Turn.PLAYER2)
            firstTurn = Turn.PLAYER1

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean
    {
        for(button in boardList)
        {
            if(button.text == "")
                return false
        }
        return true
    }

    //Check current turn(X or 0) and add to board
    private fun addToBoard(button: Button)
    {
        if(button.text != "")
            return

        if(currentTurn == Turn.PLAYER1)
        {
            button.text = PLAYER1
            currentTurn = Turn.PLAYER2
        }
        else if(currentTurn == Turn.PLAYER2)
        {
            button.text = PLAYER2
            currentTurn = Turn.PLAYER1
        }
        setTurnLabel()
    }

    //Show players turn
    private fun setTurnLabel()
    {
        var turnText = ""
        if(currentTurn == Turn.PLAYER2)
            turnText = "Turn $PLAYER2"
        else if(currentTurn == Turn.PLAYER1)
            turnText = "Turn $PLAYER1"

        binding.turnTV.text = turnText
    }

    //companion object - Define static variable and methods
    companion object
    {
        const val PLAYER1 = "O"
        const val PLAYER2 = "X"
    }

}











