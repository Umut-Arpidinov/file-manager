package kg.o.internlabs.omarket.base

interface Mapper<F,T> {

    fun fromMap(from:F):T
}