from django.contrib.auth.models import AbstractUser
from django.db import models

# Has same fields as class User (username, password, email)
class MyUser(AbstractUser):
    # add extra fields here as needed
    email = models.CharField(max_length=254, blank=True, null=True) # override this to remove its validator
    bio = models.TextField(blank=True, null=True)
    friends = models.JSONField(default=dict, blank=True, null=True)  # behaves like a Python dictionary


    # The equivalent of toString()
    # Makes sense to return the user's username
    def __str__(self):
        return self.username
