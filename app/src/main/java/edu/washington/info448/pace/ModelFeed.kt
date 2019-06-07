package edu.washington.info448.pace

class ModelFeed {
    var course: String? = null
    var feed : String? = null


    constructor():this("","") {

    }


    constructor(course: String?, feed: String?) {
        this.course = course
        this.feed = feed
    }
}