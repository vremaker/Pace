package edu.washington.info448.pace


class ModelDisc {
    var header: String? = null
    var content : String? = null


    constructor():this("","") {

    }


    constructor(header: String?, content: String?) {
        this.header = header
        this.content = content
    }
}