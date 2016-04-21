package com.ohdroid.zbmaster.application.rxbus

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ohdroid on 2016/4/21.
 */
@Singleton
class RxBus {

    @Inject
    constructor() {

    }

    val rxBus = SerializedSubject<Any, Any>(PublishSubject.create<Any>())

    fun send(event: Any) {
        rxBus.onNext(event)
    }

    fun toObservable(): Observable<Any> {
        return rxBus.asObservable()
    }

    fun hasObservables(): Boolean {
        return rxBus.hasObservers()
    }

}