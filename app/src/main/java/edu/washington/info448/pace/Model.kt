package edu.washington.info448.pace

class Model {
    var desc: String? = null
    var link : String? = null


    constructor():this("","") {

    }


    constructor(title: String?, link: String?) {
        this.desc = title
        this.link = link
    }
}