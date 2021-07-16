-- First version with no where statement
bmitell :: (RealFloat a) => a -> a -> String
bmitell w h
  | w / h ^ 2 <= 18.5 = "You're underweight"
  | w / h ^ 2 <= 25.0 = "Normal"
  | w / h ^ 2 <= 30.0 = "Fat"
 -- Second version with a where statement
bmitell' :: (RealFloat a) => a -> a -> String
bmitell' w h
  | bmi <= thin = "You're underweight"
  | bmi <= normal = "Normal"
  | bmi <= fat = "Fat"
  | otherwise = "very fat"
  where bmi = w / h ^ 2
        thin = 18.5
        normal = 25.0
        fat = 30.0
