package com.example.messengerapp.models

class Message {
    var userId:String=""
    var uMessage:String=""
    var uTime:Long=0L

    constructor(){

    }

    constructor(userId:String,uMessage: String){
        this.userId=userId
        this.uMessage=uMessage

    }
    constructor(userId:String,uMessage: String,uTime:Long){
        this.userId=userId
        this.uMessage=uMessage
        this.uTime=uTime
    }
}