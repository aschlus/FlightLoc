package ashex.flightloc

class Location(val title: String, val flown: Boolean) {

    companion object {
        private var lastLocationId = 0
        fun createLocationList(numLocations: Int) : ArrayList<Location> {
            val locations = ArrayList<Location>()
            for (i in 1..numLocations) {
                locations.add(Location("Test " + ++lastLocationId, i <= numLocations / 2))
            }
            return locations
        }
    }
}