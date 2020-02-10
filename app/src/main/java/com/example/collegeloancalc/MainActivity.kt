package com.example.collegeloancalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private var currentLoan = 0.0
    private var currentInterest = 0

    companion object{
        private val LOAN_TOTAL = "LOAN_TOTAL"
        private val CUSTOM_INTEREST = "CUSTOM_INTEREST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check if app just started or is being restored from memory
        if(savedInstanceState == null){
            currentLoan = 0.0
            currentInterest = 5
        }else{
            currentLoan = savedInstanceState.getDouble(LOAN_TOTAL)
            currentInterest = savedInstanceState.getInt(CUSTOM_INTEREST)
        }

        loanAmountText.addTextChangedListener(loanEditTextWatcher)
        seekBar.setOnSeekBarChangeListener(customSeekBarListener)
    }

    private fun updateStandard(){
        val interestRate = (currentInterest.toDouble() / 100)

        //5 year
        val fiveEMI = calEMI(currentLoan,interestRate,5)
        val fiveTotal = fiveEMI * 60
        fiveYearEMI.setText(String.format("%.02f",fiveEMI))
        fiveYearsTotal.setText(String.format("%.02f",fiveTotal))

        //10
        val tenEMI = calEMI(currentLoan,interestRate,10)
        val tenTotal = tenEMI * 120
        tenYearEMI.setText(String.format("%.02f",tenEMI))
        tenYearsTotal.setText(String.format("%.02f",tenTotal))


        //15
        val fifteenEMI = calEMI(currentLoan,interestRate,15)
        val fifteenTotal = fifteenEMI * 180
        fifteenYearsEMI.setText(String.format("%.02f",fifteenEMI))
        fifteenYearsTotal.setText(String.format("%.02f",fifteenTotal))


        //20
        val twentyEMI = calEMI(currentLoan,interestRate,20)
        val twentyTotal = twentyEMI * 240
        twentyYearsEMI.setText(String.format("%.02f",twentyEMI))
        twentyYearsTotal.setText(String.format("%.02f",twentyTotal))


        //25
        val twentyFiveEMI = calEMI(currentLoan,interestRate,25)
        val twentyfiveTotal = twentyFiveEMI * 300
        twentyFiveYearsEMI.setText(String.format("%.02f",twentyFiveEMI))
        twentyFiveYearsTotal.setText(String.format("%.02f",twentyfiveTotal))


        //30
        val thirtyEMI = calEMI(currentLoan,interestRate,30)
        val thirtyTotal = thirtyEMI * 360
        thirtyYearsEMI.setText(String.format("%.02f",thirtyEMI))
        thirtyYearsTotal.setText(String.format("%.02f",thirtyTotal))
    }

    private fun updateCustom(){
        interestRateText.text = currentInterest.toString() + "%"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putDouble(LOAN_TOTAL,currentLoan)
        outState.putInt(CUSTOM_INTEREST,currentInterest)
    }

    private val customSeekBarListener = object: SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            currentInterest = seekBar.progress
            updateCustom()
            updateStandard()
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }

    private val loanEditTextWatcher = object : TextWatcher{
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            try{
                currentLoan = s.toString().toDouble()
            } catch (e: NumberFormatException){
                currentLoan = 0.0
            }

            updateStandard()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }



    private fun calEMI(loan: Double, interest: Double, year: Int):Double{
        var p = loan
        var r = (interest / 1200)
        var n = (year * 12).toDouble()
        var a = Math.pow( (1+r), n)
        var EMI = (p * r * a) / (a - 1)
        return EMI
    }





}
