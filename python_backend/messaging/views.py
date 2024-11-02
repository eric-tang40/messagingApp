# viewsets make CRUD easy.
# This will allow us to create, read, update, and delete objects in our database easily
from rest_framework import viewsets, status
from rest_framework.response import Response

# for authentication
from django.contrib.auth import authenticate
from rest_framework.authtoken.models import Token
from rest_framework.views import APIView

# import all models and corresponding serializers
from .models import MyUser
from .jsonConversion import MyUserSerializer

class UserViewSet(viewsets.ModelViewSet):
    queryset = MyUser.objects.all()
    serializer_class = MyUserSerializer

    def create(self, request, *args, **kwargs):
        username = request.data.get('username')

        # check if the username is unique
        if MyUser.objects.filter(username=username).exists():
            return Response(status=status.HTTP_200_OK)

        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = serializer.save()

        # Return the user's data. This is necessary for our program
        user_data = MyUserSerializer(user).data
        return Response(user_data, status=status.HTTP_201_CREATED)


# used for authentication
class LoginView(APIView):
    def post(self, request):
        username = request.data.get("username")
        password = request.data.get("password")
        user = authenticate(username=username, password=password)
        if user:
            token, created = Token.objects.get_or_create(user=user)
            return Response({"token": token.key}, status=status.HTTP_200_OK)
        return Response({"error": "Invalid credentials"}, status=status.HTTP_401_UNAUTHORIZED)


