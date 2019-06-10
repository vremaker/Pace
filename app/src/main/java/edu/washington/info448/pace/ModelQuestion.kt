package edu.washington.info448.pace

class ModelQuestion {
    var question: String? = null
    var response : String? = null


    constructor():this("","") {

    }


    constructor(question: String?, response: String?) {
        this.question = question
        this.response = response
    }
}