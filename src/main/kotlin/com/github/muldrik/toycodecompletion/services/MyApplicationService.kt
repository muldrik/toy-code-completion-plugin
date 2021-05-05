package com.github.muldrik.toycodecompletion.services

import com.github.muldrik.toycodecompletion.MyBundle


class MyApplicationService {

    init {
        println(MyBundle.message("applicationService") + " kek")
        //MyDictionary.loadWords("/dictionary.txt")
    }
}
