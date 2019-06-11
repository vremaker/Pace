package edu.washington.info448.pace


class ModelDisc {
    var header: String? = null
    var content : String? = null
    var date: String? = null


    constructor():this("","", "") {

    }


    constructor(header: String?, content: String?, date: String?) {
        this.header = header
        this.content = content
        this.date = date
    }
}