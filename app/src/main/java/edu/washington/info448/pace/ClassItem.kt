package edu.washington.info448.pace

class ClassItem {
    var name: String? = null

    constructor():this("") {

    }

    constructor(title: String?) {
        this.name = title
    }

}