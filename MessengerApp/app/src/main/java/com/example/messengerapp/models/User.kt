package com.example.messengerapp.models

public class User{
    public var userName:String=""
    public var email:String=""
    public var password:String=""
    public var userId:String=""
    public var lastMessage:String=""
    public var  profilePic:String=""

    constructor(){

    }
    public constructor(userName: String, email: String, password: String ,id:String)  {
        this.userName=userName
        this.email=email
        this.password=password
        this.userId=id
    }
    constructor(userName: String,email: String,password: String,userId:String,lastMessage:String,profilePic:String){
        this.userName=userName
        this.email=email
        this.password=password
        this.userId=userId
        this.lastMessage=lastMessage
        this.profilePic=profilePic
    }
}