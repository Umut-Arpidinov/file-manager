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
        list.add(LocationX(3,"Ошская", locationType = REGION, subLocationsId =  listOf(150,119,120,122,123,124,118,121)))
        list.add(LocationX(21,"Нарынская", locationType =  REGION, subLocationsId = listOf(125,126,129,128,127)))
        list.add(LocationX(2,"Чуйская", locationType = REGION,  subLocationsId =  listOf(1,7,5,6,143,8,140,141,142,151)))
        list.add(LocationX(61,"Иссык-Кульская", locationType =  REGION,  subLocationsId = listOf(81,111,112,114,115,117,62,63,113)))
        list.add(LocationX(103,"Джалал-Абадская", locationType =  REGION, subLocationsId = listOf(152,105,106,108,109,110,104,107)))
        list.add(LocationX(101,"Таласская", locationType =  REGION, subLocationsId = listOf(135,136,139,138,137)))
        list.add(LocationX(102,"Баткенская", locationType =  REGION, subLocationsId = listOf(130,131,134,133,132)))

        // subLocations of Naryn region
        list.add(LocationX(125,"Баетово", locationType = CITY,21))
        list.add(LocationX(126,"Ат-Башы", locationType = CITY,21))
        list.add(LocationX(129,"Нарын", locationType = CITY,21))
        list.add(LocationX(128,"Кочкор", locationType =  CITY,21))
        list.add(LocationX(127,"Чаек", locationType =  CITY,21))

        // subLocations of Issyk-Kul region
        list.add(LocationX(81,"Каракол", locationType = CITY,61))
        list.add(LocationX(111,"Тюп", locationType =  CITY,61))
        list.add(LocationX(112,"Теплоключенка", locationType = CITY,61))
        list.add(LocationX(114,"Кызыл-Суу", locationType =  CITY,61))
        list.add(LocationX(115,"Чолпон-Ата", locationType =  CITY,61))
        list.add(LocationX(117,"Балыкчы", locationType =  CITY,61))
        list.add(LocationX(62,"Джети-Огуз", locationType =  PROVINCE,61))
        list.add(LocationX(63,"Тамга", locationType = CITY,61))
        list.add(LocationX(113,"Боконбаево", locationType =  CITY,61))

        // subLocations of Jalal-Abad region
        list.add(LocationX(152,"Джалал-Абад", locationType =  CITY,103))
        list.add(LocationX(105,"Кербен", locationType = CITY,103))
        list.add(LocationX(106,"Кара-Куль", locationType =  CITY,103))
        list.add(LocationX(108,"Токтогул", locationType = CITY,103))
        list.add(LocationX(109,"Кочкор-Ата", locationType = CITY,103))
        list.add(LocationX(110,"Кок-Жангак", locationType = CITY,103))
        list.add(LocationX(104,"Таш-Кумыр",locationType =  CITY,103))
        list.add(LocationX(107,"Майлуу-Суу", locationType = CITY,103))

        // subLocations of Talas region
        list.add(LocationX(135,"Бакай-Ата", locationType =  CITY,101))
        list.add(LocationX(136,"Талас", locationType =  CITY,101))
        list.add(LocationX(139,"Манас", locationType = CITY,101))
        list.add(LocationX(138,"Покровка", locationType =  CITY,101))
        list.add(LocationX(137,"Кызыл-Адыр", locationType = CITY,101))

        // subLocations of Osh region
        list.add(LocationX(150,"Ош", locationType = CITY, 3))
        list.add(LocationX(119,"Араван", locationType =  CITY, 3))
        list.add(LocationX(120,"Кара-Кульджа", locationType =  CITY, 3))
        list.add(LocationX(122,"Кара-Суу", locationType =  CITY, 3))
        list.add(LocationX(123,"Ноокат", locationType =  CITY, 3))
        list.add(LocationX(124,"Узген", locationType = CITY, 3))
        list.add(LocationX(118,"Гульча", locationType = CITY, 3))
        list.add(LocationX(121,"Дароот-Коргон", locationType = CITY, 3))

        // subLocations of Chuy region
        list.add(LocationX(1,"Бишкек", locationType =  CITY, 2,))
        list.add(LocationX(7,"Кант", locationType =  CITY, 2))
        list.add(LocationX(5,"Кара-Балта", locationType = CITY, 2))
        list.add(LocationX(6,"Сокулук", locationType =  CITY, 2))
        list.add(LocationX(143,"Каинды", locationType =  CITY, 2))
        list.add(LocationX(8,"Токмок", locationType =  CITY, 2))
        list.add(LocationX(140,"Беловодское", locationType = CITY,2))
        list.add(LocationX(141,"Лебединовка", locationType = CITY, 2))
        list.add(LocationX(142,"Кемин", locationType =  CITY, 2))
        list.add(LocationX(151,"Аламедин", locationType =  CITY, 2))

        // subLocations of Batken region

        list.add(LocationX(130,"Баткен", locationType = CITY, 102))
        list.add(LocationX(131,"Кадамжай", locationType =  CITY, 102))
        list.add(LocationX(134,"Сулюкта", locationType =  CITY, 102))
        list.add(LocationX(133,"Кызыл-Кия", locationType =  CITY, 102))
        list.add(LocationX(132,"Разаков", locationType = CITY, 102))


    }
    fun findLocationId(name:String):Int?{
        list.forEach{
            if(it.name==name) return it.id
        }
        return null
    }

}

