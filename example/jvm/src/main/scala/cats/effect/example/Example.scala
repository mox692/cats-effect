/*
 * Copyright 2020-2023 Typelevel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cats.effect
package example

import cats.Monad
import cats.effect.kernel.GenConcurrent
import cats.effect.kernel.MonadCancel

//object Example extends IOApp.Simple {
//
//  def run: IO[Unit] =
//    (IO(println("started")) >> IO.never).onCancel(IO(println("canceled")))
//}

object Blocking extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    for {
      // _ <- IO.cede
      _ <- IO(println(s"${Thread.currentThread.getName} default"))
      _ <- IO.blocking(println(s"${Thread.currentThread.getName} blocking"))
      _ <- IO(println(s"${Thread.currentThread.getName} default"))
    } yield ExitCode.Success
}

object AsyncApp extends IOApp {
  import scala.concurrent._
  import cats.effect.IO
  import cats.syntax.all._

  implicit val ec = ExecutionContext.global
  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(println(s"${Thread.currentThread.getName} default"))
      _ <- IO.blocking(println(s"${Thread.currentThread.getName} blocking"))
      _ <- IO(println(s"${Thread.currentThread.getName} default"))
    } yield ExitCode.Success
  
  def program[F[_]: Async]: F[Unit] = 
    for {
      // MEMO: Asyncの制約がついているので、もちろんAysncのmethodが呼べる
      _ <- Async[F].fromFuture(Async[F].pure(Future(4)))
      // MEMO: 加えて、Asyncは継承ヒエラルキの一番下位に位置するので
      //       その上のmethodも使える(例えばparReplicateANはConcurrentのメソッド)
      _ <- GenConcurrent[F].parReplicateAN(3)(3, Async[F].pure(4))
      // MEMO: ↓ はMonadCancel
      _ <- MonadCancel[F].canceled
    } yield ()

}
