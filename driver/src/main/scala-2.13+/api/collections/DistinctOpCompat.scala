package reactivemongo.api.collections

import scala.language.higherKinds

import scala.concurrent.{ ExecutionContext, Future }

import scala.collection.Factory

import reactivemongo.api.{ ReadConcern, SerializationPack }

import reactivemongo.api.commands.Collation

private[collections] trait DistinctOpCompat[P <: SerializationPack with Singleton] { collection: GenericCollection[P] with DistinctOp[P] =>

  final protected def distinctDocuments[T, M[_] <: Iterable[_]](
    key: String,
    query: Option[pack.Document],
    readConcern: ReadConcern,
    collation: Option[Collation])(implicit
    reader: pack.NarrowValueReader[T],
    ec: ExecutionContext,
    cbf: Factory[T, M[T]]): Future[M[T]] =
    distinctDocuments(key, query, readConcern, collation, cbf.newBuilder)
}
