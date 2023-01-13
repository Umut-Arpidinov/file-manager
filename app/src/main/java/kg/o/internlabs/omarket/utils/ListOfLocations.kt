package kg.o.internlabs.omarket.utils

import kg.o.internlabs.omarket.domain.entity.ads.LocationX

class ListOfLocations {
    val list = mutableListOf<LocationX>()
    companion object {
        const val CITY = "Город"
        const val REGION = "Область"
        const val PROVINCE = "Населенный пункт"
    }
    fun saveLocations(){
        list.add(LocationX(3,"Ошская",REGION, subLocationsId =  listOf(150,119,120,122,123,124,118,121)))
        list.add(LocationX(21,"Нарынская", REGION, subLocationsId = listOf(125,126,129,128,127)))
        list.add(LocationX(2,"Чуйская", REGION,  subLocationsId =  listOf(1,7,5,6,143,8,140,141,142,151)))
        list.add(LocationX(61,"Иссык-Кульская",  REGION,  subLocationsId = listOf(81,111,112,114,115,117,62,63,113)))
        list.add(LocationX(103,"Джалал-Абадская", REGION, subLocationsId = listOf(152,105,106,108,109,110,104,107)))
        list.add(LocationX(101,"Таласская",   REGION, subLocationsId = listOf(135,136,139,138,137)))
        list.add(LocationX(102,"Баткенская",REGION, subLocationsId = listOf(130,131,134,133,132)))

        // subLocations of Naryn region
        list.add(LocationX(125,"Баетово", CITY, 21))
        list.add(LocationX(126,"Ат-Башы",  CITY,  21))
        list.add(LocationX(129,"Нарын",  CITY, 21))
        list.add(LocationX(128,"Кочкор", CITY,  21))
        list.add(LocationX(127,"Чаек",   CITY,  21))

        // subLocations of Issyk-Kul region
        list.add(LocationX(81,"Каракол", CITY,61))
        list.add(LocationX(111,"Тюп",  CITY, 61))
        list.add(LocationX(112,"Теплоключенка", CITY, 61))
        list.add(LocationX(114,"Кызыл-Суу", CITY, 61))
        list.add(LocationX(115,"Чолпон-Ата", CITY, 61))
        list.add(LocationX(117,"Балыкчы",   CITY, 61))
        list.add(LocationX(62,"Джети-Огуз", PROVINCE, 61))
        list.add(LocationX(63,"Тамга",  CITY, 61))
        list.add(LocationX(113,"Боконбаево",   CITY,  61))

        // subLocations of Jalal-Abad region
        list.add(LocationX(152,"Джалал-Абад",  CITY, 103))
        list.add(LocationX(105,"Кербен", CITY,103))
        list.add(LocationX(106,"Кара-Куль",  CITY, 103))
        list.add(LocationX(108,"Токтогул", CITY,103))
        list.add(LocationX(109,"Кочкор-Ата", CITY, 103))
        list.add(LocationX(110,"Кок-Жангак",  CITY, 103))
        list.add(LocationX(104,"Таш-Кумыр", CITY, 103))
        list.add(LocationX(107,"Майлуу-Суу", CITY, 103))

        // subLocations of Talas region
        list.add(LocationX(135,"Бакай-Ата", CITY, 101))
        list.add(LocationX(136,"Талас",  CITY,101))
        list.add(LocationX(139,"Манас",  CITY, 101))
        list.add(LocationX(138,"Покровка",  CITY, 101))
        list.add(LocationX(137,"Кызыл-Адыр", CITY,101))

        // subLocations of Osh region
        list.add(LocationX(150,"Ош",  CITY,  3))
        list.add(LocationX(119,"Араван", CITY, 3))
        list.add(LocationX(120,"Кара-Кульджа",  CITY,  3))
        list.add(LocationX(122,"Кара-Суу", CITY, 3))
        list.add(LocationX(123,"Ноокат",  CITY, 3))
        list.add(LocationX(124,"Узген", CITY,  3))
        list.add(LocationX(118,"Гульча", CITY,  3))
        list.add(LocationX(121,"Дароот-Коргон",  CITY,  3))

        // subLocations of Chuy region
        list.add(LocationX(1,"Бишкек",  CITY,2))
        list.add(LocationX(7,"Кант", CITY, 2))
        list.add(LocationX(5,"Кара-Балта",  CITY, 2))
        list.add(LocationX(6,"Сокулук",  CITY,  2))
        list.add(LocationX(143,"Каинды",   CITY,  2))
        list.add(LocationX(8,"Токмок",  CITY,  2))
        list.add(LocationX(140,"Беловодское",  CITY, 2))
        list.add(LocationX(141,"Лебединовка", CITY, 2))
        list.add(LocationX(142,"Кемин",  CITY,  2))
        list.add(LocationX(151,"Аламедин",  CITY, 2))

        // subLocations of Batken region

        list.add(LocationX(130,"Баткен",  CITY, 102))
        list.add(LocationX(131,"Кадамжай", CITY,  102))
        list.add(LocationX(134,"Сулюкта",   CITY, 102))
        list.add(LocationX(133,"Кызыл-Кия",   CITY, 102))
        list.add(LocationX(132,"Разаков", CITY, 102))


    }
    fun findLocationId(name:String):Int?{
        list.forEach{
            if(it.name==name) return it.id
        }
        return null
    }

}

