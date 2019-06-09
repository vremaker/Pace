package edu.washington.info448.pace

class groupModel {
    var person1: String? = null
    var person2 : String? = null
    var person3 : String? = null
    var person4 : String? = null
    var person5 : String? = null


    constructor():this("","","","","") {

    }

    constructor(p1: String?, p2: String?, p3: String?, p4: String?,p5: String?) {
        this.person1 = p1
        this.person2 = p2
        this.person3 = p3
        this.person4 = p4
        this.person5 = p5
    }
}