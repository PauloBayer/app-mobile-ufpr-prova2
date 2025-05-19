package com.example.masterdetailprova2app.util

import java.util.concurrent.Executors

// Um único thread para operações de I/O (disco, rede etc.)
private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/** Executa [block] em background (IO thread). */
fun ioThread(block: () -> Unit) = IO_EXECUTOR.execute(block)