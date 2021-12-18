package kz.garage.permission.request.handler.factory

import kz.garage.permission.request.handler.RequestHandler

interface RequestHandlerFactory {
    fun provideHandler(): RequestHandler
}