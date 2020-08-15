package ashex.flightloc

class Location(val title: String, val town: String, val flown: Boolean) {

    override fun toString(): String {
        return "Title: ${this.title}, Town: ${this.town}, Flown: ${this.flown}"
    }

    companion object {
        private var lastLocationId = 0
        fun createLocationList(numLocations: Int) : ArrayList<Location> {
            val locations = ArrayList<Location>()
            for (i in 1..numLocations) {
                locations.add(Location("Location " + ++lastLocationId, "Town " + lastLocationId, i <= numLocations / 2))
            }
            lastLocationId = 0
            return locations
        }
    }
}