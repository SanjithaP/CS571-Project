module Password where

import Control.Monad.State

type PwdOp a = State String a

setPassword :: String -> PwdOp ()
setPassword newPwd = put newPwd

checkPassword :: String -> PwdOp Bool
checkPassword guess = do
  current <- get
  return (guess == current)

runPwdOp :: PwdOp a -> a
runPwdOp action = evalState action ""