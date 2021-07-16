data Person = Person { firstName :: String
                     , lastName :: String
                     , age :: Int
                     , height :: Int
                     , phoneNumber :: String
                     , flavor :: String
                     } deriving (Show)
names = map (reverse . firstName) people
  where people = [Person {firstName="Martin",lastName="Robson",age=55,height=185,phoneNumber="89898",flavor="Chilli"},
                  Person {firstName="Joe",lastName="Bloggs",age=25,height=190,phoneNumber="87987",flavor="curry"}] 
