/*
 * Copyright 2015 Dennis Vriend
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

package com.github.dnvriend

import javax.inject.{ Inject, Singleton }

import org.slf4j.LoggerFactory
import scalikejdbc._

import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.blocking
import scalaz._
import Scalaz._

object UserTableRow extends SQLSyntaxSupport[UserTableRow] {
  def apply(rs: WrappedResultSet): UserTableRow =
    UserTableRow(rs.longOpt(1), rs.string(2), rs.string(3))
}

final case class UserTableRow(id: Option[Long], first: String, last: String)

@Singleton
class UserRepository @Inject() (implicit ec: ExecutionContext) {
  val log = LoggerFactory.getLogger(this.getClass)

  // Represents that already existing session will be used or a new session will be started.
  implicit val session = AutoSession
  def withFuture[A](block: => A)(implicit session: DBSession): Future[A] = Future(blocking(block))
  def withLocalTransaction[A](block: => A) = DB.futureLocalTx(implicit session => withFuture(block))
  def toRow(rs: WrappedResultSet): UserTableRow = UserTableRow(rs)
  val utr = UserTableRow.syntax("utr")

  def allUsers: Future[List[UserTableRow]] = withFuture(sql"""select * from "users" ORDER BY id;""".map(toRow).list().apply())
  def findById(userId: Long): Future[Option[UserTableRow]] = withFuture(sql"""select * from "users" where id = ${userId};""".map(toRow).single().apply())
  def clear: Future[Boolean] = withLocalTransaction(sql"""TRUNCATE TABLE "users";""".execute().apply)
  def drop: Future[Boolean] = withLocalTransaction(sql"""DROP TABLE IF EXISTS "users";""".execute().apply)
  def create: Future[Boolean] = withLocalTransaction(sql"""CREATE TABLE IF NOT EXISTS "users" (id SERIAL, first VARCHAR(255) NOT NULL, last VARCHAR(255) NOT NULL);""".execute().apply)

  def dropCreateSchema: Future[Unit] =
    create.map(_ => ())

  def init: Future[Unit] = {
    val batchParams: Seq[Seq[String]] = Seq(
      Seq("Bill", "Gates"),
      Seq("Steve", "Balmer"),
      Seq("Steve", "Jobs"),
      Seq("Steve", "Wozniak")
    )
    Future(sql"""insert into "users" (first, last) values (?, ?)""".batch(batchParams: _*).apply()).map(_ => ())
  }
}
