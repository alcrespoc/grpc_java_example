# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
import grpc

import saludo_pb2 as saludo__pb2


class SaludarStub(object):
  """Definicion del servicio Saludar.
  """

  def __init__(self, channel):
    """Constructor.

    Args:
      channel: A grpc.Channel.
    """
    self.Saludo = channel.unary_unary(
        '/saludo.Saludar/Saludo',
        request_serializer=saludo__pb2.SaludoRequest.SerializeToString,
        response_deserializer=saludo__pb2.SaludoResponse.FromString,
        )
    self.SaludoOtraVez = channel.unary_unary(
        '/saludo.Saludar/SaludoOtraVez',
        request_serializer=saludo__pb2.SaludoRequest.SerializeToString,
        response_deserializer=saludo__pb2.SaludoResponse.FromString,
        )


class SaludarServicer(object):
  """Definicion del servicio Saludar.
  """

  def Saludo(self, request, context):
    """Saluda
    """
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')

  def SaludoOtraVez(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')


def add_SaludarServicer_to_server(servicer, server):
  rpc_method_handlers = {
      'Saludo': grpc.unary_unary_rpc_method_handler(
          servicer.Saludo,
          request_deserializer=saludo__pb2.SaludoRequest.FromString,
          response_serializer=saludo__pb2.SaludoResponse.SerializeToString,
      ),
      'SaludoOtraVez': grpc.unary_unary_rpc_method_handler(
          servicer.SaludoOtraVez,
          request_deserializer=saludo__pb2.SaludoRequest.FromString,
          response_serializer=saludo__pb2.SaludoResponse.SerializeToString,
      ),
  }
  generic_handler = grpc.method_handlers_generic_handler(
      'saludo.Saludar', rpc_method_handlers)
  server.add_generic_rpc_handlers((generic_handler,))