from venv import create

from django.urls import path, include
from rest_framework.routers import DefaultRouter  # import the router
from .views import UserViewSet, LoginView
from . import views

# router to handle CRUD
router = DefaultRouter()
router.register(r'users', UserViewSet)


urlpatterns = [
    path('', include(router.urls)),
    path('api/login/', LoginView.as_view(), name='login'),
]

""" 
Note: the path is /messaging/userprofiles/ 
What operation is done is based on the type of request sent (GET, POST, PUT) 
"""
