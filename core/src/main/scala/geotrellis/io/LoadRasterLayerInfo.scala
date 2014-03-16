/**************************************************************************
 * Copyright (c) 2014 Azavea.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **************************************************************************/

package geotrellis.io

import geotrellis._
import geotrellis.process._

object LoadRasterLayerInfo {
  def apply(n:String): LoadRasterLayerInfo = 
    LoadRasterLayerInfo(LayerId(n))

  def apply(ds:String, n:String): LoadRasterLayerInfo = 
    LoadRasterLayerInfo(LayerId(ds,n))
}

/**
  * Load the [[RasterLayerInfo]] from the raster layer with the specified name.
  */
case class LoadRasterLayerInfo(layerId:Op[LayerId]) extends Op[RasterLayerInfo] {
  def _run() = runAsync(List(layerId))
  val nextSteps:Steps = {
    case (layerId:LayerId) :: Nil =>
      LayerResult { layerLoader =>
        layerLoader.getRasterLayer(layerId).info
      }
  }
}

/**
  * Load the [[RasterLayerInfo]] from the raster layer at the specified path.
  */
case class LoadRasterLayerInfoFromPath(path:Op[String]) extends Op[RasterLayerInfo] {
  def _run() = runAsync(List(path))
  val nextSteps:Steps = {
    case (path:String) :: Nil =>
      LayerResult { layerLoader =>
        layerLoader.getRasterLayerFromPath(path).info
      }
  }
}
