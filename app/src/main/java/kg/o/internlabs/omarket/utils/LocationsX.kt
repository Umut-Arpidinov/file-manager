package kg.o.internlabs.omarket.utils

import kg.o.internlabs.omarket.domain.entity.ads.LocationX

class LocationsX {
    val list = mutableListOf<LocationX>()
    companion object {
        const val CITY = "Город"
        const val REGION = "Область"
        const val PROVINCE = "Населенный пункт"
    }
    fun saveLocations(){
        list.add(LocationX(1,"Бишкек", CITY, -1,true,2,"Поиск в Бишкеке",null))
        list.add(LocationX(150,"Ош", CITY, 0,true,3,"Поиск в Оше",null))
        list.add(LocationX(21,"Нарынская", REGION, 1,false,null,"Поиск в Нарынской области",listOf(125,126,129,128,127)))
        list.add(LocationX(2,"Чуйская", REGION, 1,false,null,"Поиск в Чуйской области", null))
        list.add(LocationX(61,"Иссык-Кульская", REGION, 1,false,null,"Поиск в Иссык-Кульской области",listOf(81,111,112,114,115,117,62,63,113)))
        list.add(LocationX(103,"Джалал-Абадская", REGION, 1,false,null,"Поиск в Джалал-Абадской области",listOf(152,105,106,108,109,110,104,107)))
        list.add(LocationX(101,"Таласская", REGION, 1,false,null,"Поиск в Таласской области",listOf(135,136,139,138,137)))
        list.add(LocationX(150,"Ошская", REGION, 0,true,null,"Поиск в Ошской области",null))
        list.add(LocationX(150,"Баткенская", REGION, 0,true,null,"Поиск в Ошской области",null))

        // subLocations of Naryn region
        list.add(LocationX(125,"Баетово", CITY,1,false,21,"Поиск в Баетово",null))
        list.add(LocationX(126,"Ат-Башы", CITY,1,false,21,"Поиск в Ат-Башы",null))
        list.add(LocationX(129,"Нарын", CITY,1,false,21,"Поиск в Нарыне",null))
        list.add(LocationX(128,"Кочкор", CITY,1,false,21,"Поиск в Кочкоре",null))
        list.add(LocationX(127,"Чаек", CITY,1,false,21,"Поиск в Чаеке",null))

        // subLocations of Issyk-Kul region
        list.add(LocationX(81,"Каракол", CITY,1,false,61,"Поиск в Караколе",null))
        list.add(LocationX(111,"Тюп", CITY,1,false,61,"Поиск в Тюпе",null))
        list.add(LocationX(112,"Теплоключенка", CITY,1,false,61,"Поиск в Теплоключенке",null))
        list.add(LocationX(114,"Кызыл-Суу", CITY,1,false,61,"Поиск в Кызыл-Суу",null))
        list.add(LocationX(115,"Чолпон-Ата", CITY,1,false,61,"Поиск в Чолпон-Ате",null))
        list.add(LocationX(117,"Балыкчы", CITY,1,false,61,"Поиск в Балыкчы",null))
        list.add(LocationX(62,"Джети-Огуз", CITY,1,false,61,"Поиск в Джети-Огузе",null))
        list.add(LocationX(63,"Тамга", CITY,1,false,61,"Поиск в Тамге",null))
        list.add(LocationX(113,"Боконбаево", CITY,1,false,61,"Поиск в Боконбаево",null))

        // subLocations of Jalal-Abad region
        list.add(LocationX(152,"Джалал-Абад", CITY,0,false,103,"Поиск в Джалал-Абаде",null))
        list.add(LocationX(105,"Кербен", CITY,1,false,103,"Поиск в Кербене",null))
        list.add(LocationX(106,"Кара-Куль", CITY,1,false,103,"Поиск в Кара-Куле",null))
        list.add(LocationX(108,"Токтогул", CITY,1,false,103,"Поиск в Токтогуле",null))
        list.add(LocationX(109,"Кочкор-Ата", CITY,1,false,103,"Поиск в Кочкор-Ате",null))
        list.add(LocationX(110,"Кок-Жангак", CITY,1,false,103,"Поиск в Кок-Жангаке",null))
        list.add(LocationX(104,"Таш-Кумыр", CITY,1,false,103,"Поиск в Таш-Кумыре",null))
        list.add(LocationX(107,"Майлуу-Суу", CITY,1,false,103,"Поиск в Майлуу-Суу",null))

        // subLocations of Talas region
        list.add(LocationX(135,"Бакай-Ата", CITY,1,false,101,"Поиск в Бакай-Ата",null))
        list.add(LocationX(136,"Талас", CITY,1,false,101,"Поиск в Таласе",null))
        list.add(LocationX(139,"Манас", CITY,1,false,101,"Поиск в Манасе",null))
        list.add(LocationX(138,"Покровка", CITY,1,false,101,"Поиск в Покровке",null))
        list.add(LocationX(137,"Кызыл-Адыр", CITY,1,false,101,"Поиск в Кызыл-Адыре",null))



    }
    fun findLocationId(name:String):Int?{
        list.forEach{
            if(it.name==name) return it.id
        }
        return null
    }

}

