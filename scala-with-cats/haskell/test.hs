{-# LANGUAGE TypeSynonymInstances #-}
{-# LANGUAGE FlexibleInstances #-}

data Pair = Pair String String 

data Person = Person { name :: String
		     , email :: String }

data MyString = MyString String 

class Printable a where
   format :: a -> String

instance Printable Pair where 
   format (Pair a b) = "1st " ++ format a ++ " 2nd " ++ format b

instance Printable MyString where 
   format (MyString a ) = "MyString " ++ format a 

instance Printable String where 
   format s = "String " ++ show s 

instance Printable Integer where 
   format a = "Integer " ++ show a 

instance Printable Person where 
   format p = "Name :" ++ (name p) ++ " email:" ++ (email p)
