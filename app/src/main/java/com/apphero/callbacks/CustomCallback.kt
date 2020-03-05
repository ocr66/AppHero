package com.apphero.callbacks

import com.apphero.dataclass.Result

interface CustomCallback {
    fun updateAdapter()

    fun getMoreHeroes(lastHero: Int)

    fun viewHeroDetails(heroId: Result)
}