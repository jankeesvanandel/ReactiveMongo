package reactivemongo.api

import scala.concurrent.{ ExecutionContext, Future }

import reactivemongo.core.protocol.Response

/**
 * Cursor wrapper, to help to define custom cursor classes.
 * @see CursorProducer
 */
trait WrappedCursor[T] extends Cursor[T] with WrappedCursorCompat[T] {
  /** The underlying cursor */
  def wrappee: Cursor[T]

  def foldResponses[A](z: => A, maxDocs: Int = -1)(suc: (A, Response) => Cursor.State[A], err: Cursor.ErrorHandler[A])(implicit @deprecatedName('ctx) ec: ExecutionContext): Future[A] = wrappee.foldResponses(z, maxDocs)(suc, err)

  def foldResponsesM[A](z: => A, maxDocs: Int = -1)(suc: (A, Response) => Future[Cursor.State[A]], err: Cursor.ErrorHandler[A])(implicit @deprecatedName('ctx) ec: ExecutionContext): Future[A] = wrappee.foldResponsesM(z, maxDocs)(suc, err)

  def foldBulks[A](z: => A, maxDocs: Int = -1)(suc: (A, Iterator[T]) => Cursor.State[A], err: Cursor.ErrorHandler[A])(implicit @deprecatedName('ctx) ec: ExecutionContext): Future[A] = wrappee.foldBulks(z, maxDocs)(suc, err)

  def foldBulksM[A](z: => A, maxDocs: Int = -1)(suc: (A, Iterator[T]) => Future[Cursor.State[A]], err: Cursor.ErrorHandler[A])(implicit @deprecatedName('ctx) ec: ExecutionContext): Future[A] = wrappee.foldBulksM(z, maxDocs)(suc, err)

  def foldWhile[A](z: => A, maxDocs: Int = -1)(suc: (A, T) => Cursor.State[A], err: Cursor.ErrorHandler[A])(implicit @deprecatedName('ctx) ec: ExecutionContext): Future[A] = wrappee.foldWhile(z, maxDocs)(suc, err)

  def foldWhileM[A](z: => A, maxDocs: Int = -1)(suc: (A, T) => Future[Cursor.State[A]], err: Cursor.ErrorHandler[A])(implicit @deprecatedName('ctx) ec: ExecutionContext): Future[A] = wrappee.foldWhileM(z, maxDocs)(suc, err)

  def head(implicit @deprecatedName('ctx) ec: ExecutionContext): Future[T] = wrappee.head

  override def headOption(implicit @deprecatedName('ctx) ec: ExecutionContext): Future[Option[T]] = wrappee.headOption
}
