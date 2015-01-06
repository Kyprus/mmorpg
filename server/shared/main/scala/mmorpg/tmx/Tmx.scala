package mmorpg.tmx

import upickle.{Js, key}

object Tmx {

  case class Map(version: Int, orientation: String,
                 width: Int, height: Int,
                 @key("renderorder") renderOrder: String,
                 tilesets: Seq[Tileset],
                 layers: Seq[Layer]) {

    private val collisionLayer = layers.find(_.name == "collision")

    /**
     * Checks for a collision tile at the given index
     * @param tileIndex The tileIndex
     * @return true if the tile exists on the collision layer
     */
    def isSolid(tileIndex: Int): Boolean =
      tileIndex < 0 ||
      collisionLayer.exists(_.data(tileIndex) != 0)
  }

  case class Tileset(name: String,
                     @key("firstgid") firstGid: Int,
                     @key("tilewidth") tileWidth: Int,
                     @key("tileheight") tileHeight: Int,
                     @key("image") imagePath: String,
                     @key("imagewidth") imageWidth: Int,
                     @key("imageheight") imageHeight: Int,
                     @key("tiles") tileInfos: Seq[TileInfo])

  case class TileInfo(id: Int, animations: Seq[TileAnimation])

  object TileInfo {
    implicit val reader = upickle.Reader[Seq[TileInfo]] {
      case Js.Obj(values@_*) =>
        values.map { case (id, data) =>
          val animations = upickle.readJs[Seq[TileAnimation]](data("animation"))
          TileInfo(id.toInt, animations)
        }
    }
  }

  case class TileAnimation(duration: Int, tileid: Int)

  case class Layer(name: String,
                   data: Array[Int],
                   width: Int,
                   height: Int,
                   opacity: Double,
                   visible: Boolean)

}
