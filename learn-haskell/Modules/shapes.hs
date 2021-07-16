import qualified Geometry.Sphere as Sphere
import qualified Geometry.Cuboid as Cuboid
import qualified Geometry.Cube as Cube

main = 
  let a = 2
      b = 5
      c = 7
   in
      do
      putStrLn $ "Volume of sphere with radius " ++ show a ++ " = " ++ show (Sphere.volume a)
      putStrLn $ "Area of sphere with radius  " ++ show a ++ " = " ++ show (Sphere.area a)
      putStrLn $ "Volume of cuboid " ++ show (Cuboid.volume a b c)
      putStrLn $ "Area of cuboid "  ++ show (Cuboid.area a b c)
      putStrLn $ "Volume of Cube " ++ show a ++ " = " ++ show (Cube.volume a)
      putStrLn $ "Area of Cube " ++ show a ++ " = " ++ show (Cube.area a)
