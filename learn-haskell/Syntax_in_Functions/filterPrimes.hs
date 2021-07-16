primes :: (Integral a) => [a]
primes = filterPrimes [2..]
  where filterPrimes (p:xs) =
          p : filterPrimes [ x | x <- xs, x `mod` p /= 0 ]
