package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP=3.00
class OrderViewModel: ViewModel() {




    val dateOptions = getPickupOptions()

    private val _quantity=MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavour=MutableLiveData<String>()
    val flvaour:LiveData<String> =_flavour

    private val _date=MutableLiveData<String>()
    val date:LiveData<String> =_date

    private  val _price=MutableLiveData<Double>()
    val price:LiveData<String> = Transformations.map(_price){
        NumberFormat.getInstance().format(it)
    }


    fun SetQuantity(numberCupackes:Int){
        _quantity.value=numberCupackes
        updateprice()

    }

    fun SetFlavour(desiredFlavour:String){
        _flavour.value=desiredFlavour
    }

    fun SetDate(pickupdate:String){
        _date.value=pickupdate
    }

    fun hasNoflavourSet():Boolean{
        return _flavour.value.isNullOrEmpty()
    }

    private fun updateprice(){
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if(dateOptions[0]==_date.value){
            calculatedPrice+= PRICE_FOR_SAME_DAY_PICKUP

        }
        _price.value=calculatedPrice
    }

    private fun getPickupOptions():List<String>{
        val options= mutableListOf<String>()
        val formatter=SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar=Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    fun restorder(){
        _quantity.value=0
        _date.value=dateOptions[0]
        _price.value=0.0
        _flavour.value=""
    }
    init{
        restorder()
    }

}